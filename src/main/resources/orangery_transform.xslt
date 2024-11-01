<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!-- Main template -->
    <xsl:template match="/">
        <GroupedFlowers>
            <!-- Get unique values of Origin -->
            <xsl:for-each select="//Flower[not(Origin=preceding-sibling::Flower/Origin)]">
                <Group>
                    <Origin>
                        <xsl:value-of select="Origin"/>
                    </Origin>
                    <FlowersByOrigin>
                        <!-- Group flowers by their Origin -->
                        <xsl:for-each select="//Flower[Origin=current()/Origin]">
                            <Flower id="{@id}">
                                <Name><xsl:value-of select="Name"/></Name>
                                <Soil><xsl:value-of select="Soil"/></Soil>
                                <VisualParameters>
                                    <StemColor><xsl:value-of select="VisualParameters/StemColor"/></StemColor>
                                    <LeafColor><xsl:value-of select="VisualParameters/LeafColor"/></LeafColor>
                                    <AverageSize><xsl:value-of select="VisualParameters/AverageSize"/></AverageSize>
                                </VisualParameters>
                                <GrowingTips>
                                    <Temperature><xsl:value-of select="GrowingTips/Temperature"/></Temperature>
                                    <Lighting><xsl:value-of select="GrowingTips/Lighting"/></Lighting>
                                    <Watering><xsl:value-of select="GrowingTips/Watering"/></Watering>
                                </GrowingTips>
                                <Multiplying><xsl:value-of select="Multiplying"/></Multiplying>
                            </Flower>
                        </xsl:for-each>
                    </FlowersByOrigin>
                </Group>
            </xsl:for-each>
        </GroupedFlowers>
    </xsl:template>
</xsl:stylesheet>
